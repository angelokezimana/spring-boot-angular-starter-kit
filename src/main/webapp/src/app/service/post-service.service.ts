import { Injectable } from '@angular/core';


let apiUrl = "http://localhost:8080/api/v1/posts";

@Injectable({
  providedIn: 'root'
})
export class PostServiceService {

  constructor() { }
}
