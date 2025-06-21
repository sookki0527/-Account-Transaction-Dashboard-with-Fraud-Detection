import { Injectable } from '@angular/core';
import {Client, IMessage} from "@stomp/stompjs";
import {BehaviorSubject} from "rxjs";
import {Notification} from "../model/notification.model";
import SockJS from "sockjs-client";

@Injectable({
  providedIn: 'root'
})
export class AiNotificationService {
  private stompClient: Client;

  private notificationsAI$ = new BehaviorSubject<Notification | null>(null);

  constructor() {
    this.stompClient = new Client({
      brokerURL: undefined, // use SockJS
      webSocketFactory: () => new SockJS('http://localhost:8083/ws'), // Spring에서 `/ws`로 매핑된 엔드포인트
      reconnectDelay: 5000
    });

    this.stompClient.onConnect = frame => {
      console.log('Connected: ' + frame);
      this.stompClient.subscribe('/topic/notifications-ai', (message: IMessage) => {
        const notif: Notification = JSON.parse(message.body);
        this.notificationsAI$.next(notif);
        console.log('🔔 Notification:', notif);
      });
    };

    this.stompClient.activate();
  }

  getNotificationStream() {
    return this.notificationsAI$.asObservable();
  }
}
