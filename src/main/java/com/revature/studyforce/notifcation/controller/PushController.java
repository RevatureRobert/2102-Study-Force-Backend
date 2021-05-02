package com.revature.studyforce.notifcation.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.revature.studyforce.notifcation.model.Subscription;
import com.revature.studyforce.notifcation.service.SubscriptionService;
import com.revature.studyforce.user.model.User;
import javafx.application.Application;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.interfaces.ECPublicKey;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This service will deal with the all the operations dealing with the subscriptions Table Basically
 * all the subscription destination
 */
@CrossOrigin(origins = {"http://localhost:4500", "**"})
@RestController
public class PushController {

  private final Map<String, Subscription> subscriptions = new ConcurrentHashMap<>();

  private final SubscriptionService subscriptionService;

  public PushController(SubscriptionService subscriptionService) {
    this.subscriptionService = subscriptionService;
  }

  private final String publicKey =
      "BEH36g-ez23QfnT8OIbnZJMmj892dDYa_LKyGz_wM2tyZbSt1YK4Jy1sRz1OyAeilAOBDrg-TnCBLFtWdVIApK8";

  private final String privateKey = "VzVcwmu7b3eu55MUQD_h2pna4DAI702aluxbJnjOcxs";

  /**
   * This endpoint is used to retrieve the public key
   *
   * @return
   */
  @CrossOrigin(origins = {"http://localhost:4500", "**"})
  @GetMapping(path = "/publicSigningKey", produces = "application/octet-stream")
  public @ResponseBody byte[] publicSigningKey() {
    return this.publicKey.getBytes(StandardCharsets.UTF_8);
  }

  @CrossOrigin(origins = {"http://localhost:4500"})
  @PostMapping(path = "/subscribe")
  public @ResponseBody Subscription subscribe(@RequestBody Subscription subscription) {
    return this.subscriptionService.add(subscription);
  }

  @CrossOrigin(origins = {"http://localhost:4200"})
  @GetMapping(path = "/subscribe")
  public @ResponseBody List<Subscription> seeAllSubs() {
    return this.subscriptionService.findAll();
  }

  @CrossOrigin(origins = {"http://localhost:4200"})
  @GetMapping(path = "/users", produces = "application/hal+json")
  public @ResponseBody Optional<User> findUser(@RequestParam int id) {
    return this.subscriptionService.findUser(id);
  }

  /**
   * In this method we will pass in all the subscriptions Then we go thorough one by one and send
   * out those notifications
   *
   * @param subscriptions
   * @return
   */
  private boolean sendMessage(List<Subscription> subscriptions) {

    // Go through each subscription
    String origin = null;
    for (Subscription sub : subscriptions) {
      // Make sure thats a its a valid endpoint
      try {
        URL url = new URL(sub.getEndpoint());
        origin = url.getProtocol() + "://" + url.getHost();
      } catch (MalformedURLException e) {
        e.printStackTrace();
      }

        Date today = new Date();
          Date expires = new Date(today.getTime() + 12 * 60 * 60 * 1000);
      URI endpointURI = URI.create(sub.getEndpoint());
          String token = JWT.create().withAudience(origin).withExpiresAt(expires)
              .withSubject("mailto:example@example.com").sign(this.jwtAlgorithm);HttpRequest request = httpRequestBuilder
              .uri(endpointURI)
              .header("TTL", "180")
              .header(
                  "Authorization",
                  "vapid t=" + token + ", k=" + this.publicKey)
              .build();
    }

    return true;
  }

  //  /**
  //   * * author https://github.com/ralscha/blog2019/commits?author=ralscha * * Github repo :
  //   * https://github.com/ralscha/blog2019
  //   *
  //   * @return true if the subscription is no longer valid and can be removed, false if everything
  // is
  //   *     okay
  //   */
  //  private boolean sendPushMessage(Subscription subscription, byte[] body) {
  //        String origin = null;
  //        try {
  //            URL url = new URL(subscription.getEndpoint());
  //            origin = url.getProtocol() + "://" + url.getHost();
  //        }
  //        catch (MalformedURLException e) {
  ////            Application.logger.error("create origin", e);
  //            return true;
  //        }
  //
  //        Date today = new Date();
  //        Date expires = new Date(today.getTime() + 12 * 60 * 60 * 1000);
  //
  //        String token = JWT.create().withAudience(origin).withExpiresAt(expires)
  //                .withSubject("mailto:example@example.com").sign(this.jwtAlgorithm);
  //
  //        URI endpointURI = URI.create(subscription.getEndpoint());
  //
  //        Builder httpRequestBuilder = HttpRequest.newBuilder();
  //        if (body != null) {
  //            httpRequestBuilder.POST(BodyPublishers.ofByteArray(body))
  //                    .header("Content-Type", "application/octet-stream")
  //                    .header("Content-Encoding", "aes128gcm");
  //        }
  //        else {
  //            httpRequestBuilder.POST(BodyPublishers.ofString(""));
  //            httpRequestBuilder.header("Content-Length", "0");
  //        }
  //
  //        HttpRequest request = httpRequestBuilder.uri(endpointURI).header("TTL", "180")
  //                .header("Authorization",
  //                        "vapid t=" + token + ", k=" + this.serverKeys.getPublicKeyBase64())
  //                .build();
  //        try {
  //            HttpResponse<Void> response = this.httpClient.send(request,
  //                    BodyHandlers.discarding());
  //
  //            switch (response.statusCode()) {
  //                case 201:
  //                    Application.logger.info("Push message successfully sent: {}",
  //                            subscription.getEndpoint());
  //                    break;
  //                case 404:
  //                case 410:
  //                    Application.logger.warn("Subscription not found or gone: {}",
  //                            subscription.getEndpoint());
  //                    // remove subscription from our collection of subscriptions
  //                    return true;
  //                case 429:
  //                    Application.logger.error("Too many requests: {}", request);
  //                    break;
  //                case 400:
  //                    Application.logger.error("Invalid request: {}", request);
  //                    break;
  //                case 413:
  //                    Application.logger.error("Payload size too large: {}", request);
  //                    break;
  //                default:
  //                    Application.logger.error("Unhandled status code: {} / {}",
  // response.statusCode(),
  //                            request);
  //            }
  //        }
  //        catch (IOException | InterruptedException e) {
  //            Application.logger.error("send push message", e);
  //        }
  //
  //        return false;
  //    }

}
