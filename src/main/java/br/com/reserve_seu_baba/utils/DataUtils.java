package br.com.reserve_seu_baba.utils;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

public class DataUtils {
	public DataUtils() {}
	
	public static Timestamp parseTimestamp(String dateTimeStr) throws Exception {
		LocalDateTime localDateTime;
		Timestamp sqlTimestamp = Timestamp.valueOf(LocalDateTime.now()); 
		if (dateTimeStr != null && !dateTimeStr.isEmpty()) {
			localDateTime = LocalDateTime.parse(dateTimeStr);
			sqlTimestamp = Timestamp.valueOf(localDateTime);
		}
		return sqlTimestamp;
	}
	
	public static int differenceBetweenTime(Timestamp dataInicio, Timestamp dataFim) throws Exception {
		Duration duracao = Duration.between(dataInicio.toInstant(), dataFim.toInstant());
		return (int) duracao.toHoursPart();
	}
	
}
