package cj.software.hpfc.weather.imports.entity;

import java.time.Instant;
import java.util.Objects;

import com.datastax.driver.extras.codecs.jdk8.InstantCodec;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(name = "tmp_wind_u")
public class TmpWindU
{
	@PartitionKey(0)
	@Column(name = "lokation_bezeichnung")
	private String lokationBezeichnung;

	@PartitionKey(1)
	@Column(name = "prognosezeitpunkt", codec = InstantCodec.class)
	private Instant prognoseZeitpunkt;

	@PartitionKey(2)
	@Column(name = "zeitpunkt", codec = InstantCodec.class)
	private Instant zeitpunkt;

	@Column(name = "wert")
	private double wert;

	private TmpWindU()
	{
	}

	public TmpWindU(String pLokationBezeichnung, Instant pPrognoseZeitpunkt, Instant pZeitpunkt, double pWert)
	{
		this();
		this.setLokationBezeichnung(pLokationBezeichnung);
		this.setPrognoseZeitpunkt(pPrognoseZeitpunkt);
		this.setZeitpunkt(pZeitpunkt);
		this.setWert(pWert);
	}

	public String getLokationBezeichnung()
	{
		return this.lokationBezeichnung;
	}

	public Instant getPrognoseZeitpunkt()
	{
		return this.prognoseZeitpunkt;
	}

	public Instant getZeitpunkt()
	{
		return this.zeitpunkt;
	}

	public double getWert()
	{
		return this.wert;
	}

	private void setLokationBezeichnung(String pLokationBezeichnung)
	{
		this.lokationBezeichnung = Objects.requireNonNull(pLokationBezeichnung);
	}

	private void setPrognoseZeitpunkt(Instant pPrognoseZeitpunkt)
	{
		this.prognoseZeitpunkt = Objects.requireNonNull(pPrognoseZeitpunkt);
	}

	private void setZeitpunkt(Instant pZeitpunkt)
	{
		this.zeitpunkt = Objects.requireNonNull(pZeitpunkt);
	}

	private void setWert(double pWert)
	{
		this.wert = pWert;
	}

}
