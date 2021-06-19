package io.osnz;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kefeng Deng (deng@51any.com)
 */
@SpringBootApplication
@RestController
@EnableWebMvc
@Log4j2
public class Application {

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(Application.class);
    app.setBannerMode(Banner.Mode.OFF);
    app.run(args);
    log.info("Server is ready ...");
  }

  @GetMapping(path = "/**", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Map> index(HttpServletRequest request, @RequestHeader HttpHeaders headers) {
    log.info("Server V2 received request URI : {}", request.getRequestURI());
    Map<String, Object> response = new HashMap<>();
    response.put("Version", "V2");
    response.put("URI", request.getRequestURI());
    response.put("URL", request.getRequestURL().toString());
    response.put("Method", request.getMethod());
    response.put("Query String", request.getQueryString());
    response.put("Remote address", request.getRemoteAddr());
    response.put("Remote host", request.getRemoteHost());
    response.put("Headers", headers);
    return ResponseEntity.ok(response);
  }

  @RequestMapping("favicon.ico")
  void favicon() {
  }

  @GetMapping("/log/error")
  String error() {
    log.error("Hitting error");
    return "Error";
  }

  @GetMapping("/log/warning")
  String warning() {
    log.warn("waring message");
    return "warning";
  }

  @GetMapping("/log/info")
  String info() {
    log.info("info message");
    return "info";
  }

  @GetMapping("/log/debug")
  String debug() {
    log.debug("debug");
    return "debug";
  }

}
