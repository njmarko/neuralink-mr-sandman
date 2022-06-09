import { Component, OnInit } from '@angular/core';
import { WebSocketService } from './service/web-socket-service/web-socket.service';

import * as uuid from 'uuid';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'neuralink-frontend';

  constructor(private socketService: WebSocketService) {

  }

  ngOnInit(): void {
    this.socketService.initializeWebSocketConnection();
  }
}
