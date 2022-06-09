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

  constructor() { }

  initializeWebSocketConnection() {
    if (!this.isLoaded) {
      let ws = new SockJS(this.url);
      this.stompClient = Stomp.over(ws);
      this.stompClient.connect({}, () => {
        this.isLoaded = true;
      });
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
