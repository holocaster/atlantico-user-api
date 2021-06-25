package br.gov.inst.atlan.userapi.domain.enums;

public enum PerfilEnum {

	ADMIN(1, "ROLE_ADMIN"), USER(2, "ROLE_USER");

	private Integer id;
	private String descricao;

	private PerfilEnum(Integer id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public Integer getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public static PerfilEnum get(Integer id) {
		for (final PerfilEnum item : PerfilEnum.values()) {
			if (item.getId().equals(id)) {
				return item;
			}
		}

		throw new IllegalArgumentException();
	}

}
