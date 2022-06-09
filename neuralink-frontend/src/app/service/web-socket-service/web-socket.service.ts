import { HttpClient } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';

import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { DeviceSendSignal } from 'src/app/model/DeviceSendSignal';
import { SignalReceivedResponse } from 'src/app/model/SignalReceivedResponse';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  url: string = 'http://localhost:8080/socket/';
  private stompClient!: any;
  public isLoaded: boolean = false;
  notifier: EventEmitter<SignalReceivedResponse> = new EventEmitter<SignalReceivedResponse>();

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
          '/live-signals',
          (message: { body: string }) => {
            this.handleResult(message);
          }
        );
      } catch {
        console.log('Connection has not been established yet... connecting...');
      }
    }
  }

  onSignalRecieved() {
    return this.notifier;
  }

  handleResult(message: { body: string }): void {
    if (message.body) {
      const response = JSON.parse(message.body);
      this.notifier.emit(response);
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
