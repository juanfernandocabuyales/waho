CREATE OR REPLACE FUNCTION woaho.generar_aleatorios(pInicial INT ,pFinal INT) 
RETURNS character varying 
LANGUAGE plpgsql
AS $$
BEGIN
   RETURN floor(random()* (pFinal-pInicial + 1) + pInicial);
END;
$$