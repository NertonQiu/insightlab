package com.acxiom.insightlab.api.model;

import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class PersonicxPortraitModel {

	private PersonicxTargetModel target;
	private PersonicxReferenceModel reference;
	private List<PersonicxPortraitRow> portraitData;

	public PersonicxTargetModel getTarget() {
		return target;
	}

	public void setTarget(PersonicxTargetModel target) {
		this.target = target;
	}

	public PersonicxReferenceModel getReference() {
		return reference;
	}

	public void setReference(PersonicxReferenceModel reference) {
		this.reference = reference;
	}

	public List<PersonicxPortraitRow> getPortraitData() {
		return portraitData;
	}

	public void setPortraitData(List<PersonicxPortraitRow> portraitData) {
		this.portraitData = portraitData;
	}

	@Override
	public int hashCode() {
		final HashCodeBuilder builder = new HashCodeBuilder();

		builder.append(this.target);
		builder.append(this.reference);
		builder.append(this.portraitData);

		return builder.toHashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (!(obj instanceof PersonicxPortraitModel)) {
			return false;
		}

		final PersonicxPortraitModel other = (PersonicxPortraitModel) obj;

		final EqualsBuilder builder = new EqualsBuilder();

		boolean isEquals = builder.isEquals();

		builder.append(this.portraitData, other.getPortraitData());
		builder.append(this.reference, other.getReference());
		builder.append(this.target, other.getTarget());

		return isEquals;

	}
}
