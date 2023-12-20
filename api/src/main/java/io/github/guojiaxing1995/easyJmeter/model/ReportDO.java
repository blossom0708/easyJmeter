package io.github.guojiaxing1995.easyJmeter.model;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "report")
public class ReportDO implements Serializable {

    private static final long serialVersionUID = 4026728833071475370L;

    @Id
    private String taskId;

    private Map<String, List<JSONObject>> dashBoardData;

    private Map<String, JSONObject> graphData;

    private JFileDO file;
}
