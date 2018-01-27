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

public class YamaRecoSteps {

    var areaList: AreaListDto? = null

    @Step("エリアリストを取得する")
    fun setAreaList(){
        areaList = getAreaListResult()
        print(areaList)
    }

    @Step("エリアリストには全<areaCount>の地域がある")
    fun verifyAreacount(areaCount: Int){
        assertThat(areaList?.arealist?.size,`is`(areaCount))
    }

    @Step("その地域には以下の様な国内から海外までの地域がidと名前で定義されている <areas>")
    fun verifyAreaDetail(areas: Table){
        areas.tableRows.map {  r ->
            AreaDto(Integer.parseInt(r.getCell("id")),r.getCell("地域名"))
        }.forEach{ e ->
            Assert.assertEquals("Not contain " + e + " in " + areaList,true,areaList!!.arealist.contains(e))
        }
    }

    data class AreaListDto (
            val arealist: List<AreaDto>
    )

    data class AreaDto (
            val area_id: Int,
            val area:   String
    )

    fun getAreaListResult(): AreaListDto? {
        val tmp = connect("https://api.yamareco.com/api/v1/getArealist")
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                .adapter(AreaListDto::class.java).fromJson(tmp)
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
