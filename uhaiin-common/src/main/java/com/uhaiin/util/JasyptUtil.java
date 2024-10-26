package com.uhaiin.util;


import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentPBEConfig;

public class JasyptUtil {

	public static final String DEFAULT_ALGORITHM = "PBEWITHMD5ANDDES";

	private JasyptUtil() {
	}

	/**
	 * 使用默认算法和指定的密码对字符串进行加密。
	 * <p>
	 * 此方法提供了一个简化的加密流程，其中默认的加密算法被应用于输入的字符串。 它适用于那些不需要自定义加密算法且满足默认算法安全性的场景。
	 *
	 * @param encryptedStr 需要加密的字符串
	 * @param password     用于加密的密码
	 * @return 加密后的字符串
	 */
	public static String encrypt(String encryptedStr, String password) {
		return encrypt(encryptedStr, DEFAULT_ALGORITHM, password);
	}

	/**
	 * 使用Jasypt加密给定的字符串。
	 * <p>
	 * 此方法提供了对敏感信息进行加密的能力，以保护数据的安全性。它使用了预定义的加密算法和一个密码来加密输入的字符串。
	 *
	 * @param encryptedStr 需要被加密的原始字符串。
	 * @param algorithm    加密算法的名称，定义了加密过程使用的特定算法。
	 * @param password     用于加密过程的密码，增加了加密的强度。
	 * @return 返回加密后的字符串。
	 */
	public static String encrypt(String encryptedStr, String algorithm, String password) {
		// 创建一个StandardPBEStringEncryptor实例，用于执行实际的加密操作。
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		// 创建一个EnvironmentPBEConfig实例，用于从环境变量中读取配置信息。
		EnvironmentPBEConfig config = new EnvironmentPBEConfig();

		// 设置加密算法和密码。
		config.setAlgorithm(algorithm);
		config.setPassword(password);

		// 将配置应用到加密器中。
		encryptor.setConfig(config);

		// 执行加密操作并返回加密后的字符串。
		return encryptor.encrypt(encryptedStr);
	}

	/**
	 * 使用默认算法和提供的密码进行解密操作。
	 * <p>
	 * 此方法为 decrypt(String, String, String) 的重载版本，它使用预定义的默认算法。
	 * 主要用于简化调用，当解密需求的算法与默认算法相同时，无需指定算法名称。
	 *
	 * @param decryptStr 需要解密的字符串，它是由加密算法加密后的结果。
	 * @param password   用于解密的密码，必须与加密时使用的密码相同。
	 * @return 解密后的原始字符串。
	 */
	public static String decrypt(String decryptStr, String password) {
		// 调用带算法名称的解密方法，使用默认算法和提供的密码进行解密
		return decrypt(decryptStr, DEFAULT_ALGORITHM, password);
	}

	/**
	 * 使用Jasypt加密库进行解密操作。
	 * <p>
	 * 本函数旨在提供一个通用的解密接口，通过指定加密算法和密码，对加密后的字符串进行解密。
	 * 支持的加密算法包括PBEWITHMD5ANDDES、PBEWITHMD5ANDTRIPLEDES、PBEWITHSHA1ANDDESEDE、PBEWITHSHA1ANDRC2_40等。
	 *
	 * @param decryptStr 需要解密的字符串。
	 * @param algorithm  解密使用的算法，必须与加密时使用的算法一致。
	 * @param password   解密使用的密码，必须与加密时使用的密码一致。
	 * @return 解密后的原始字符串。
	 */
	public static String decrypt(String decryptStr, String algorithm, String password) {
		// 创建一个标准的PBE字符串加密器，用于实际的解密操作。
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		// 创建环境配置对象，用于从系统环境变量中读取加密配置。
		EnvironmentPBEConfig config = new EnvironmentPBEConfig();

		// 设置解密使用的算法。
		config.setAlgorithm(algorithm);
		// 设置解密使用的密码。
		config.setPassword(password);
		// 将配置应用到加密器中。
		encryptor.setConfig(config);

		// 执行解密操作，返回解密后的字符串。
		// 解密
		return encryptor.decrypt(decryptStr);
	}

}