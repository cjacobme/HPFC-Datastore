package cj.software.hpfc.weather.imports.dao;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;

import cj.software.hpfc.weather.imports.entity.TmpWindU;

@Accessor
public interface TmpWindUAccessor
{
	@Query("select * from tmp_wind_u limit :max")
	public Result<TmpWindU> listTmpWindU(@Param("max") int pLimit);
}
