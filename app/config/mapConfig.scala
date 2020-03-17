package config

import java.io.File

import org.apache.commons.io.FileUtils
import utils.Utils.path

import com.alibaba.fastjson.JSON

object mapConfig {

  val loaction = Array("北京" , "天津" , "上海", "重庆", "河北", "山西", "辽宁", "吉林", "黑龙江", "江苏", "浙江", "安徽", "福建",
    "江西", "山东", "河南", "湖北", "湖南", "广东", "海南", "四川", "贵州", "云南", "陕西", "甘肃", "青海",
    "台湾", "内蒙古", "广西", "西藏", "宁夏", "新疆","香港","澳门","南海诸岛")


  def addSpeciesInfo(index : Int , number : Int) : Unit = {
    val file = new File(path,"config/china.json")
    val text = FileUtils.readFileToString(file)
    val json =JSON.parseObject(text)
    val features = json.getJSONArray("features")
    val info = features.get(index).toString
    val jsons = JSON.parseObject(info)
    val properties = jsons.getJSONObject("properties")
    properties.put("species", number)
    features.remove(index)
    features.add(index,jsons)
    file.delete()
    FileUtils.writeStringToFile(file,json.toString)
  }

}
