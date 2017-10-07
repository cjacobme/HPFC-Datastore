package cj.software.hpfc.weather.imports.dao;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;

import cj.software.hpfc.weather.imports.entity.TmpWindV;

@Accessor
public interface TmpWindVAccessor
{
	@Query("select * from tmp_wind_v limit :max")
	public Result<TmpWindV> listTmpWindV(@Param("max") int pLimit);
}
