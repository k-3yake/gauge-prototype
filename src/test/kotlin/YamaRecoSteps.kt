import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.thoughtworks.gauge.Step
import com.thoughtworks.gauge.Table
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

import org.hamcrest.MatcherAssert.*
import org.hamcrest.Matchers.`is`
import org.junit.Assert
import java.io.File

public class YamaRecoSteps {

    var areaList: AreaListDto? = null

    @Step("エリアリストを<url>から取得します")
    fun setAreaList(url: String){
        areaList = convertAreaListDto(connect("https://api.yamareco.com/api/v1" + url))
    }

    @Step("エリアリストには全<areaCount>の地域があります")
    fun verifyAreacount(areaCount: Int){
        assertThat(areaList?.arealist?.size,`is`(areaCount))
    }

    @Step("その地域には以下の様に国内から海外まで、各地域がidと名前の組み合わせで定義されています <areas>")
    fun verifyAreaDetail(areas: Table){
        areas.tableRows.map {  r ->
            AreaDto(Integer.parseInt(r.getCell("id")),r.getCell("名前"))
        }.forEach{ e ->
            Assert.assertEquals("Not contain " + e + " in " + areaList,true,areaList!!.arealist.contains(e))
        }
    }

    @Step("実行結果のjson例はこちらをご覧ください <file:./src/test/resources/getAreaListExample.json>")
    fun verifyJson(file: String){
        Assert.assertEquals(convertAreaListDto(file),areaList)
    }

    data class AreaListDto (
            val arealist: List<AreaDto>
    )

    data class AreaDto (
            val area_id: Int,
            val area:   String
    )

    fun convertAreaListDto(json: String): AreaListDto? {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                .adapter(AreaListDto::class.java).fromJson(json)
    }

    fun connect(requestUrl: String): String {

        //  URLオブジェクトを生成
        val url = URL(requestUrl)

        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection

        urlConnection.requestMethod = "GET"

        urlConnection.connect()


        val br = BufferedReader(InputStreamReader(urlConnection.inputStream))

        var line: String? = null
        val sb = StringBuilder()

        for (line in br.readLines()) {
            line?.let { sb.append(line) }
        }

        br.close()

        return sb.toString()

    }
}
