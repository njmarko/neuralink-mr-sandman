import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegisterUserRequest } from 'src/app/model/RegisterUserRequest';
import { User } from 'src/app/model/User';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {

  }

  register(request: RegisterUserRequest): Observable<User> {
    return this.http.post<User>('http://localhost:8080/api/users', request);
  }
}
