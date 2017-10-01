package cj.software.hpfc.lokation.dao;

import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;

import cj.software.hpfc.lokation.entity.Lokation;

@Accessor
public interface LokationAccessor
{
	@Query("SELECT * FROM lokation WHERE bucket = 1 AND bezeichnung = :bezeichnung")
	public Lokation readLokationDetails(@Param("bezeichnung") String pBezeichnung);
}
