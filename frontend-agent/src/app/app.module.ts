import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { AppComponent } from './app.component';
import { AuthComponent } from './auth/auth/auth.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule, DatePipe } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material.module';
import { PostsComponent } from './post/posts/posts.component';
import { NewPostComponent } from './post/posts/create/new-post/new-post.component';
import { NewCommentComponent } from './comment/new-comment/new-comment.component';
import { EditCommentComponent } from './comment/edit/edit-comment/edit-comment.component';
import { ToastrModule } from 'ngx-toastr';
import { RatingModule } from 'ng-starrating';

@NgModule({
  declarations: [
    AppComponent,
    AuthComponent,
    PostsComponent,
    NewPostComponent,
    NewCommentComponent,
    EditCommentComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    CommonModule,
    MatCardModule,
    BrowserAnimationsModule,
    MaterialModule,
    RatingModule,
    NgxWebstorageModule.forRoot(),
    ToastrModule.forRoot({ timeOut: 2000, enableHtml: true })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
