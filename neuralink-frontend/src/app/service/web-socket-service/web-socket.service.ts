import { HttpClient } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';

import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { DeviceSendSignal } from 'src/app/model/DeviceSendSignal';
import { SignalReceivedResponse } from 'src/app/model/SignalReceivedResponse';
import { EventNotification } from 'src/app/model/EventNotification';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  url: string = 'http://localhost:8080/socket/';
  private stompClient!: any;
  public isLoaded: boolean = false;
  signalNotifier: EventEmitter<SignalReceivedResponse> = new EventEmitter<SignalReceivedResponse>();
  eventNotifier: EventEmitter<EventNotification> = new EventEmitter<EventNotification>();

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
          (event: { body: string }) => {
            this.handleSignalEvent(event);
          }
        );
        this.stompClient.subscribe(
          '/live-events',
          (event: { body: string }) => {
            this.handleNotificationEvent(event);
          }
        )
      } catch {
        console.log('Connection has not been established yet... connecting...');
      }
    }
  }

  onSignalRecieved() {
    return this.signalNotifier;
  }

  onEventNotification() {
    return this.eventNotifier;
  }

  handleSignalEvent(message: { body: string }): void {
    if (message.body) {
      const response = JSON.parse(message.body);
      this.signalNotifier.emit(response);
    }
  }

  handleNotificationEvent(message: { body: string }): void {
    if (message.body) {
      const response = JSON.parse(message.body);
      this.eventNotifier.emit(response);
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
