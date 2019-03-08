import { Component } from '@angular/core';
import { AuthentificationService } from './services/authentification.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'etutorat-cli';

  constructor(private authentificationService: AuthentificationService) { }

  ngOnInit() {
    this.authentificationService.checkSignin();
  }
}
