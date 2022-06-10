import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegisterUserRequest } from 'src/app/model/RegisterUserRequest';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {

  }

  register(request: RegisterUserRequest): Observable<any> {
    return this.http.post<any>('http://localhost:8080/api/users', request);
  }
}
