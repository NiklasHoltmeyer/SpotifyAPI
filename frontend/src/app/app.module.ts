import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserprofileComponent } from './component/spotify/user/userprofile/userprofile.component';
import { DemoComponent } from './component/page/demo/demo.component';
import { NavbarComponent } from './component/common/navbar/navbar.component';

@NgModule({
  declarations: [
    AppComponent,
    UserprofileComponent,
    DemoComponent,
    NavbarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
     RouterModule.forRoot([
        {path: 'demo', component: DemoComponent},
      ]),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
