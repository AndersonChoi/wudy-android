package com.wudy.newsmakers.opengraph;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class OpenGraphVO implements Serializable {

    private String ogTitle;
    private String ogDescription;
    private String ogUrl;
    private String ogImage;

}
