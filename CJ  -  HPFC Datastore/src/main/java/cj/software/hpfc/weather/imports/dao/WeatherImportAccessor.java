package cj.software.hpfc.weather.imports.dao;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;

import cj.software.hpfc.weather.imports.entity.ImportDirectory;

@Accessor
public interface WeatherImportAccessor
{
	@Query("select * from directory_finished where bucket=1 and directory_name in (?, ?)")
	public Result<ImportDirectory> listDirectories(String... pDirNames);
}
