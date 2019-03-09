package challenge;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

	private static final Path FILE_PATH = new File(Main.class.getClassLoader().getResource("data.csv").getFile())
			.toPath();
	private static final String COMMA = ",";
	private static final String ARQUIVO_NAO_ENCONTRADO = "Arquivo não encontrado";

	private String convertEncoding(String string) {
		try {
			return new String(string.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	// Quantas nacionalidades (coluna `nationality`) diferentes existem no arquivo?
	public long q1() {
		try (Stream<String> stream = Files.lines(FILE_PATH).skip(1)) {
			return stream.map(line -> line.split(COMMA)[14]).distinct().count();
		} catch (IOException e) {
			logFileNotFound();
			return 0;
		}
	}

	// Quantos clubes (coluna `club`) diferentes existem no arquivo?
	// Obs: Existem jogadores sem clube.
	public long q2() {
		try (Stream<String> stream = Files.lines(FILE_PATH).skip(1)) {
			return stream.map(line -> line.split(COMMA)[3]).distinct().filter(player -> !player.isEmpty()).count();
		} catch (IOException e) {
			logFileNotFound();
			return 0;
		}
	}

	// Liste o primeiro nome (coluna `full_name`) dos 20 primeiros jogadores.
	public List<String> q3() {
		try (Stream<String> stream = Files.lines(FILE_PATH).skip(1)) {
			return stream.map(line -> line.split(COMMA)[2]).limit(20).map(this::convertEncoding)
					.collect(Collectors.toList());
		} catch (IOException e) {
			logFileNotFound();
			return null;
		}
	}

	// Quem são os top 10 jogadores que possuem as maiores cláusulas de rescisão?
	// (utilize as colunas `full_name` e `eur_release_clause`)
	public List<String> q4() {
		try (Stream<String> stream = Files.lines(FILE_PATH).skip(1)) {
			return stream.map(player -> {
				String[] splitedString = player.split(COMMA);
				return new Player(splitedString[2],
						splitedString[18].isEmpty() ? 0.0 : Double.parseDouble(splitedString[18]));
			}).sorted(Comparator.comparing(Player::getReleaseValueClause).reversed()).limit(10).map(Player::getName)
					.map(this::convertEncoding).collect(Collectors.toList());
		} catch (IOException e) {
			logFileNotFound();
			return null;
		}
	}

	// Quem são os 10 jogadores mais velhos (use como critério de desempate o
	// campo `eur_wage`)?
	// (utilize as colunas `full_name` e `birth_date`)
	public List<String> q5() {
		try (Stream<String> stream = Files.lines(FILE_PATH).skip(1)) {
			return stream.parallel().map(player -> {
				String[] splitedString = player.split(COMMA);
				return new Player(splitedString[2],
						splitedString[8].isEmpty() ? null : LocalDate.parse(splitedString[8]),
						splitedString[18].isEmpty() ? 0.0 : Double.parseDouble(splitedString[18]));
			}).sorted(Comparator.comparing(Player::getBirth, Comparator.nullsLast(Comparator.naturalOrder()))
					.thenComparing(Player::getWageValue)).limit(10).map(Player::getName).map(this::convertEncoding)
					.collect(Collectors.toList());
		} catch (IOException e) {
			logFileNotFound();
			return null;
		}
	}

	// Conte quantos jogadores existem por idade. Para isso, construa um mapa onde
	// as chaves são as idades e os valores a contagem.
	// (utilize a coluna `age`)
	public Map<Integer, Integer> q6() {
		try (Stream<String> stream = Files.lines(FILE_PATH).skip(1)) {
			return stream.map(line -> Integer.parseInt(line.split(COMMA)[6]))
					.collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(x -> 1)));
		} catch (IOException e) {
			logFileNotFound();
			return null;
		}
	}

	private void logFileNotFound() {
		System.out.println(ARQUIVO_NAO_ENCONTRADO);
	}

}
