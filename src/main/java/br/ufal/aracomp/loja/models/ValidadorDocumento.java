package br.ufal.aracomp.loja.models;

public class ValidadorDocumento {
	private ValidadorDocumento() {
	}

	public static boolean documentoInformado(String documento) {
		return documento != null && !documento.trim().isEmpty();
	}

	public static String normalizar(String documento) {
		if (documento == null) {
			return "";
		}

		return documento.trim()
				.replace(".", "")
				.replace("-", "")
				.replace("/", "")
				.replaceAll("\\s", "")
				.toUpperCase();
	}
}
