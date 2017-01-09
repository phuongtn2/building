package com.building.util.str;

import java.io.IOException;

public interface CSVLineReader<T> {
	T readData(String line) throws IOException;
}
