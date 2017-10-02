package cj.software.hpfc.weather.imports.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import cj.software.hpfc.weather.imports.entity.ImportDirectory;

@Dependent
public class WeatherImportDAO implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Inject
	private Session session;

	public List<ImportDirectory> listDirectories(String... pSearched)
	{
		String lInClause = this.toInClause(pSearched);
		String lQL = "SELECT * FROM directory_finished WHERE bucket=1 AND directory_name in (" + lInClause + ")";
		ResultSet lRead = this.session.execute(lQL);
		List<Row> lRows = lRead.all();
		List<ImportDirectory> lResult = new ArrayList<>();
		for (Row bRow : lRows)
		{
			String lDirName = bRow.getString("directory_name");
			boolean lFinished = bRow.getBool("directory_finished");
			ImportDirectory lImportDirectory = new ImportDirectory(lDirName, lFinished);
			lResult.add(lImportDirectory);
		}
		return lResult;
	}

	private String toInClause(String... pItems)
	{
		StringBuilder lSB = new StringBuilder();
		for (String bItem : pItems)
		{
			lSB.append("'").append(bItem).append("', ");
		}
		int lLength = lSB.length();
		lSB.delete(lLength - 2, lLength);
		return lSB.toString();
	}
}
