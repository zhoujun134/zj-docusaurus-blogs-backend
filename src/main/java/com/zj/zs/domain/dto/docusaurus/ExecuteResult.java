package com.zj.zs.domain.dto.docusaurus;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ExecuteResult {
    private List<String> infoMessage = Lists.newArrayList();
    private List<String> errorMessage = Lists.newArrayList();
    private int exitCode;
}
