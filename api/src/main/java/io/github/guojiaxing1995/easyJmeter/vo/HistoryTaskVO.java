package io.github.guojiaxing1995.easyJmeter.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.guojiaxing1995.easyJmeter.common.enumeration.TaskResultEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryTaskVO {

    private String taskId;

    private String creator;

    private String jmeterCase;

    private TaskResultEnum result;

    private Integer numThreads;

    private Integer duration;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
