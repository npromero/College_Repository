import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class StockAnalyzer implements StockAnalyzerInterface {
	ArrayList<AbstractStock> allStocks = new ArrayList<> (  );

	/**
	 * Loads stock data from the specified file.
	 * Merges the data with previously loaded data.
	 * <p>
	 * Data files are formatted in a Comma Separated Values (CSV) text file format
	 * Each line contains the following values:
	 * Date,Open,High,Low,Close,Adj Close,Volume
	 * <p>
	 * The first line of the file contains the column headings.
	 * <p>
	 * NOTES:
	 * 1. Ignore the Adj Close data.
	 * 2. You must convert the Date to a timestamp.
	 * 3. The stock ticker symbol is the file name minus the file extension.
	 * 4. Skip (Reject) any stock whose data is invalid.
	 *
	 * @param file
	 * @return The list of stocks read in from the specified file.
	 * @throws FileNotFoundException
	 */
	@Override
	public ArrayList< AbstractStock > loadStockData ( File file ) throws FileNotFoundException {
		ArrayList<AbstractStock> list = new ArrayList<> (  );
		String filename = file.getName ();
		String symbol = filename.substring ( 0, filename.indexOf ( ".csv" ) );
		try( Scanner fileInput = new Scanner ( file ) ) {
			fileInput.nextLine ();
			while( fileInput.hasNext () ) {
				String line = fileInput.nextLine ();
				Scanner stringInput = new Scanner( line );
				stringInput.useDelimiter ( "," );
				Long timestamp = makeDateTimestamp ( stringInput.next () );
				if ( !stringInput.hasNextDouble () ) {
					// Reject
					continue;
				}
				double open = stringInput.nextDouble ();
				double high = stringInput.nextDouble ();
				double low = stringInput.nextDouble ();
				double close = stringInput.nextDouble ();
				stringInput.next (  ); // Skip Adj Close
				double volume = stringInput.nextDouble ();
				list.add ( new Stock( symbol, timestamp, open, high, low, close, volume ) );
			}
			allStocks.addAll ( list );
		}
		return list;
	}

	private Long makeDateTimestamp( String dateString ) {
		Instant instant = Instant.now(); //can be LocalDateTime
		ZoneId systemZone = ZoneId.systemDefault(); // my timezone
		ZoneOffset currentOffsetForMyZone = systemZone.getRules().getOffset(instant);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse ( dateString, formatter );
		return date.atTime ( 16, 0, 0 ).toEpochSecond ( currentOffsetForMyZone );
	}

	/**
	 * @return a list of all stocks that have been loaded
	 */
	@Override
	public ArrayList< AbstractStock > listStocks ( ) {
		return allStocks;
	}

	/**
	 * @param symbol
	 * @return a list of all stocks with the specified ticker symbol.
	 */
	@Override
	public ArrayList< AbstractStock > listBySymbol ( String symbol ) {
		ArrayList<AbstractStock> list = new ArrayList<> (  );
		for( AbstractStock stock : allStocks ) {
			if ( stock.getSymbol ().equals( symbol ) ) {
				list.add ( stock );
			}
		}
		return list;
	}

	/**
	 * @param symbol
	 * @param startDate
	 * @param endDate
	 * @return a list of stocks with the specified symbol recorded between
	 * 		  (and including) the start and end dates.
	 */
	@Override
	public ArrayList< AbstractStock > listBySymbolDates ( String symbol,
	                                                      LocalDate startDate,
	                                                      LocalDate endDate ) {
		ArrayList<AbstractStock> list = new ArrayList<> (  );
		for( AbstractStock stock : listBySymbol ( symbol ) ) {
			Instant instant = Instant.ofEpochSecond ( stock.getTimestamp () );
			LocalDate date = instant.atZone ( ZoneId.systemDefault () ).toLocalDate ();
			if ( date.compareTo ( startDate ) >= 0 && date.compareTo ( endDate ) <= 0 ) {
				list.add ( stock );
			}
		}
		return list;
	}

	/**
	 * @param symbol
	 * @param startDate
	 * @param endDate
	 * @return the average high value of all stocks with the specified symbol
	 * 		  recorded between (and including) the start and end dates.
	 * @throws StockNotFoundException
	 */
	@Override
	public double averageHigh ( String symbol, LocalDate startDate, LocalDate endDate )
			  throws StockNotFoundException {
		ArrayList<AbstractStock> list = listBySymbolDates ( symbol, startDate, endDate );
		if ( list.isEmpty () ) {
			throw new StockNotFoundException ( symbol );
		}
		double averageHigh = 0;
		for( AbstractStock stock : list ) {
			averageHigh += stock.getHigh ();
		}
		return averageHigh / list.size ();
	}

	/**
	 * @param symbol
	 * @param startDate
	 * @param endDate
	 * @return the average low value of all stocks with the specified symbol
	 * 		  recorded between (and including) the start and end dates.
	 * @throws StockNotFoundException
	 */
	@Override
	public double averageLow ( String symbol, LocalDate startDate, LocalDate endDate )
			  throws StockNotFoundException {
		ArrayList<AbstractStock> list = listBySymbolDates ( symbol, startDate, endDate );
		if ( list.isEmpty () ) {
			throw new StockNotFoundException ( symbol );
		}
		double averageLow = 0;
		for( AbstractStock stock : list ) {
			averageLow += stock.getLow ();
		}
		return averageLow / list.size ();
	}

	/**
	 * @param symbol
	 * @param startDate
	 * @param endDate
	 * @return the average volume value of all stocks with the specified symbol
	 * 		  recorded between (and including) the start and end dates.
	 * @throws StockNotFoundException
	 */
	@Override
	public double averageVolume ( String symbol, LocalDate startDate, LocalDate endDate )
			  throws StockNotFoundException {
		ArrayList<AbstractStock> list = listBySymbolDates ( symbol, startDate, endDate );
		if ( list.isEmpty () ) {
			throw new StockNotFoundException ( symbol );
		}
		double averageVolume = 0;
		for( AbstractStock stock : list ) {
			averageVolume += stock.getVolume ();
		}
		return averageVolume / list.size ();
	}

	public static void main ( String[] args ) throws FileNotFoundException, StockNotFoundException {
		StockAnalyzer stockAnalyzer = new StockAnalyzer ();
		stockAnalyzer.loadStockData ( new File( "AAPL.csv" ) );
		stockAnalyzer.loadStockData ( new File( "MSFT.csv" ) );
		ArrayList<AbstractStock> list = stockAnalyzer.listStocks ();
		System.out.println( stockAnalyzer.listBySymbolDates ( "AAPL", LocalDate.of(2019,6,1), LocalDate.of(2019,6,30) ) );
		System.out.println( stockAnalyzer.averageHigh ( "AAPL", LocalDate.of(2019,6,1), LocalDate.of(2019,6,30) ));
		System.out.println( stockAnalyzer.averageHigh ( "MSFT", LocalDate.of(2019,6,1), LocalDate.of(2019,6,30) ));
		System.out.println( stockAnalyzer.averageHigh ( "SPCE", LocalDate.of(2019,6,1), LocalDate.of(2019,6,30) ));
//		System.out.println( stockAnalyzer.makeDateTimestamp ( "1990-02-26" ));
	}
}
