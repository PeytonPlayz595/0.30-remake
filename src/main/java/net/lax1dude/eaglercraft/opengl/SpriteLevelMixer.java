package net.lax1dude.eaglercraft.opengl;

import static net.lax1dude.eaglercraft.opengl.RealOpenGLEnums.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.lax1dude.eaglercraft.internal.PlatformOpenGL.*;

import net.lax1dude.eaglercraft.EagRuntime;
import net.lax1dude.eaglercraft.internal.IProgramGL;
import net.lax1dude.eaglercraft.internal.IShaderGL;
import net.lax1dude.eaglercraft.internal.IUniformGL;
import net.lax1dude.eaglercraft.internal.buffer.FloatBuffer;
import net.lax1dude.eaglercraft.vector.Matrix3f;

/**
 * Copyright (c) 2022-2023 lax1dude. All Rights Reserved.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 */
public class SpriteLevelMixer {

	private static final Logger LOGGER = LogManager.getLogger("SpriteLevelMixer");

	public static final String fragmentShaderPath = "/assets/eagler/glsl/texture_mix.fsh";
	public static final String fragmentShaderPrecision = "precision lowp int;\nprecision highp float;\nprecision highp sampler2D;\n";

	private static IProgramGL shaderProgram = null;

	private static IUniformGL u_textureLod1f = null;
	private static IUniformGL u_blendFactor4f = null;
	private static IUniformGL u_blendBias4f = null;
	private static IUniformGL u_matrixTransform = null;

	private static FloatBuffer matrixCopyBuffer = null;

	private static boolean blendColorChanged = true;
	private static float blendColorR = 1.0f;
	private static float blendColorG = 1.0f;
	private static float blendColorB = 1.0f;
	private static float blendColorA = 1.0f;

	private static boolean biasColorChanged = true;
	private static float biasColorR = 0.0f;
	private static float biasColorG = 0.0f;
	private static float biasColorB = 0.0f;
	private static float biasColorA = 0.0f;

	private static boolean matrixChanged = true;
	private static final Matrix3f transformMatrix = new Matrix3f();

	private static final Matrix3f identityMatrix = new Matrix3f();

	static void initialize() {
		String fragmentSource = EagRuntime.getRequiredResourceString(fragmentShaderPath);

		IShaderGL frag = _wglCreateShader(GL_FRAGMENT_SHADER);

		_wglShaderSource(frag, GLSLHeader.getFragmentHeaderCompat(fragmentSource, fragmentShaderPrecision));
		_wglCompileShader(frag);

		if (_wglGetShaderi(frag, GL_COMPILE_STATUS) != GL_TRUE) {
			LOGGER.error("Failed to compile GL_FRAGMENT_SHADER \"" + fragmentShaderPath + "\" for SpriteLevelMixer!");
			String log = _wglGetShaderInfoLog(frag);
			if (log != null) {
				String[] lines = log.split("(\\r\\n|\\r|\\n)");
				for (int i = 0; i < lines.length; ++i) {
					LOGGER.error("[FRAG] {}", lines[i]);
				}
			}
			throw new IllegalStateException("Fragment shader \"" + fragmentShaderPath + "\" could not be compiled!");
		}

		shaderProgram = _wglCreateProgram();

		_wglAttachShader(shaderProgram, DrawUtils.vshLocal);
		_wglAttachShader(shaderProgram, frag);

		if (EaglercraftGPU.checkOpenGLESVersion() == 200) {
			VSHInputLayoutParser.applyLayout(shaderProgram, DrawUtils.vshLocalLayout);
		}

		_wglLinkProgram(shaderProgram);

		_wglDetachShader(shaderProgram, DrawUtils.vshLocal);
		_wglDetachShader(shaderProgram, frag);

		_wglDeleteShader(frag);

		if (_wglGetProgrami(shaderProgram, GL_LINK_STATUS) != GL_TRUE) {
			LOGGER.error("Failed to link shader program for SpriteLevelMixer!");
			String log = _wglGetProgramInfoLog(shaderProgram);
			if (log != null) {
				String[] lines = log.split("(\\r\\n|\\r|\\n)");
				for (int i = 0; i < lines.length; ++i) {
					LOGGER.error("[LINK] {}", lines[i]);
				}
			}
			throw new IllegalStateException("Shader program for SpriteLevelMixer could not be linked!");
		}

		matrixCopyBuffer = EagRuntime.allocateFloatBuffer(9);

		EaglercraftGPU.bindGLShaderProgram(shaderProgram);

		u_textureLod1f = _wglGetUniformLocation(shaderProgram, "u_textureLod1f");
		u_blendFactor4f = _wglGetUniformLocation(shaderProgram, "u_blendFactor4f");
		u_blendBias4f = _wglGetUniformLocation(shaderProgram, "u_blendBias4f");
		u_matrixTransform = _wglGetUniformLocation(shaderProgram, "u_matrixTransform");

		_wglUniform1i(_wglGetUniformLocation(shaderProgram, "u_inputTexture"), 0);

	}

	public static void setBlendColor(float r, float g, float b, float a) {
		if (r != blendColorR || g != blendColorG || b != blendColorB || a != blendColorA) {
			blendColorChanged = true;
			blendColorR = r;
			blendColorG = g;
			blendColorB = b;
			blendColorA = a;
		}
	}

	public static void setBiasColor(float r, float g, float b, float a) {
		if (r != biasColorR || g != biasColorG || b != biasColorB || a != biasColorA) {
			biasColorChanged = true;
			biasColorR = r;
			biasColorG = g;
			biasColorB = b;
			biasColorA = a;
		}
	}

	public static void setIdentityMatrix() {
		setMatrix3f(identityMatrix);
	}

	public static void setMatrix3f(Matrix3f matrix) {
		if (!matrix.equals(transformMatrix)) {
			matrixChanged = true;
			transformMatrix.load(matrix);
		}
	}

	public static void drawSprite(float level) {
		EaglercraftGPU.bindGLShaderProgram(shaderProgram);

		if (EaglercraftGPU.checkTextureLODCapable()) {
			_wglUniform1f(u_textureLod1f, level);
		} else {
			if (level != 0.0f) {
				LOGGER.error("Tried to copy from mipmap level {}, but this GPU does not support textureLod!", level);
			}
			_wglUniform1f(u_textureLod1f, 0.0f);
		}

		if (blendColorChanged) {
			_wglUniform4f(u_blendFactor4f, blendColorR, blendColorG, blendColorB, blendColorA);
			blendColorChanged = false;
		}

		if (biasColorChanged) {
			_wglUniform4f(u_blendBias4f, biasColorR, biasColorG, biasColorB, biasColorA);
			biasColorChanged = false;
		}

		if (matrixChanged) {
			matrixCopyBuffer.clear();
			transformMatrix.store(matrixCopyBuffer);
			matrixCopyBuffer.flip();
			_wglUniformMatrix3fv(u_matrixTransform, false, matrixCopyBuffer);
			matrixChanged = false;
		}

		DrawUtils.drawStandardQuad2D();
	}

	public static void destroy() {
		if (matrixCopyBuffer != null) {
			EagRuntime.freeFloatBuffer(matrixCopyBuffer);
			matrixCopyBuffer = null;
		}
		if (shaderProgram != null) {
			_wglDeleteProgram(shaderProgram);
			shaderProgram = null;
		}
		u_textureLod1f = null;
		u_blendFactor4f = null;
		u_blendBias4f = null;
		u_matrixTransform = null;
	}

}
