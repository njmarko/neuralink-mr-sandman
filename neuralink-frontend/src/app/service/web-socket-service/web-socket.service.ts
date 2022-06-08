import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { DeviceSendSignal } from 'src/app/model/DeviceSendSignal';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  url: string = 'http://localhost:8080/socket/';
  private stompClient!: any;
  public isLoaded: boolean = false;

  constructor(private http: HttpClient) { }

  initializeWebSocketConnection() {
    if (!this.isLoaded) {
      let ws = new SockJS(this.url);
      this.stompClient = Stomp.over(ws);
      this.stompClient.connect({}, () => {
        this.isLoaded = true;
        this.openGlobalSocket();
      });
    }
  }

  openGlobalSocket() {
    if (this.isLoaded) {
      try {
        this.stompClient.subscribe(
          '/live-signal',
          (message: { body: string }) => {
            this.handleResult(message);
          }
        );
      } catch {
        console.log('Connection has not been established yet... connecting...');
      }
    }
  }

  handleResult(message: { body: string }): void {
    if (message.body) {
      const response = JSON.parse(message.body);
      console.log(response);
    }
  }

  sendMessageUsingSocket(request: DeviceSendSignal) {
    this.stompClient.send(
      '/device-signal/send',
      {},
      JSON.stringify(request)
    );
  }

  disconnect() {
    this.stompClient.disconnect();
  }
}
