ALTER TABLE gerador
ADD CONSTRAINT unique_nome_power UNIQUE (nome_gerador, power);