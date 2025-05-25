package com.tortora.gerador.services

import com.tortora.gerador.domain.GeradorEntity
import com.tortora.gerador.domain.GeradorMapper
import com.tortora.gerador.repository.GeradorRepository
import com.tortora.gerador.representation.GeradorRepresentation
import jakarta.transaction.Transactional
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.IOException
import java.io.InputStream
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class GeradorServiceImpl(private val repository: GeradorRepository): GeradorService {

    private val logger = LoggerFactory.getLogger(GeradorService::class.java)

    @Transactional
    override fun extractData(url: String) {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url(url)
            .build()

        logger.info("Start extract data: " + LocalDateTime.now())

        client.newCall(request).execute().use { response ->
            val inputStream = getInputStream(response)
            val reader = inputStream.bufferedReader()

            reader.useLines { lines ->
                val iterator = lines.iterator()
                val (namePosition, potencyPosition) = validateHeader(iterator)

                val entities = mutableListOf<GeradorEntity>()

                while (iterator.hasNext()) {
                    val line = iterator.next()
                    val columns = line.split(",")

                    val entity = GeradorEntity(
                        nomeGerador = columns.getOrNull(namePosition) ?: "",
                        power = toBigDecimal(columns.getOrNull(potencyPosition))
                    )

                    entities.add(entity)
                }

                val distinctList = entities.distinctBy { Pair(it.nomeGerador, it.power) }
                repository.saveAll(distinctList)

                logger.info("Total saved: ${distinctList.size}")
            }

            logger.info("Finish extract data: " + LocalDateTime.now())
        }
    }

    override fun getBestGenerators(): List<GeradorRepresentation> {
        val generators = repository.findTop5ByOrderByPowerDesc()
        return GeradorMapper.toRepresentationList(generators)
    }

    private fun getInputStream(response: Response): InputStream {
        if (!response.isSuccessful) {
            throw IOException("Falha na requisição: ${response.code}")
        }

        return response.body?.byteStream() ?: throw Exception("Erro ao realizar download do arquivo. Body vazio")
    }

    private fun validateHeader(iterator: Iterator<String>): Pair<Int, Int> {
        if (!iterator.hasNext()) {
            throw Exception("Arquivo CSV vazio ou sem header")
        }

        val header = iterator.next().split(",")

        val namePosition = getHeaderPosition(header, "NomEmpreendimento")
        val potencyPosition = getHeaderPosition(header, "MdaPotenciaOutorgadaKw")

        if (namePosition == -1 || potencyPosition == -1) {
            throw Exception("Colunas não foram encontradas")
        }
        return Pair(namePosition, potencyPosition)
    }

    private fun getHeaderPosition(header: List<String>, columnName: String): Int {
        return header.indexOf(columnName).takeIf { it != -1 }
            ?: throw Exception("Coluna '$columnName' não encontrada no header")
    }

    private fun toBigDecimal(value: String?): BigDecimal {
        val regexValue = value?.replace("\"", "")

        return regexValue?.toBigDecimal() ?: BigDecimal.ZERO
    }

}