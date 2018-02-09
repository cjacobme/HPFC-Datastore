package cj.software.hpfc.weather.imports.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.datastax.driver.core.ResultSetFuture;

public class BulkyOperationResult
		implements
		Serializable
{
	private static final long serialVersionUID = 1L;

	private long inFlights;

	private boolean rejectedTooManyPending = false;

	private List<ResultSetFuture> futures = new ArrayList<>();

	private BulkyOperationResult()
	{
	}

	public long getInFlights()
	{
		return this.inFlights;
	}

	public List<ResultSetFuture> getFutures()
	{
		return Collections.unmodifiableList(this.futures);
	}

	public boolean isRejectedTooManyPending()
	{
		return this.rejectedTooManyPending;
	}

	public static Builder builder()
	{
		return new Builder();
	}

	public static class Builder
	{
		protected BulkyOperationResult instance;

		protected Builder()
		{
			this.instance = new BulkyOperationResult();
		}

		public Builder withInFlights(long pInFlights)
		{
			this.instance.inFlights = pInFlights;
			return this;
		}

		public Builder addResultSetFutures(ResultSetFuture... pFutures)
		{
			if (pFutures != null && pFutures.length > 0)
			{
				this.instance.futures.addAll(Arrays.asList(pFutures));
			}
			return this;
		}

		public Builder addResultSetFutures(Collection<ResultSetFuture> pFutures)
		{
			if (pFutures != null)
			{
				this.instance.futures.addAll(pFutures);
			}
			return this;
		}

		public Builder rejected()
		{
			this.instance.rejectedTooManyPending = true;
			return this;
		}

		public BulkyOperationResult build()
		{
			BulkyOperationResult lResult = this.instance;
			this.instance = null;
			return lResult;
		}
	}
}
