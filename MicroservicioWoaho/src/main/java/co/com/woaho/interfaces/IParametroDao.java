package co.com.woaho.interfaces;

import java.util.List;
import java.util.Map;

public interface IParametroDao {

	Map<String, String> obtenerParametrosCorreo(List<String> pList);
}
