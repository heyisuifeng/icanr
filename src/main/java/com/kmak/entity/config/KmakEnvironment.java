package com.kmak.entity.config;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by Leaf.Ye on 2017/3/6.
 *
 * 自定义环境类
 */
@Component
public class KmakEnvironment {

    @Inject
    Environment env;
}
