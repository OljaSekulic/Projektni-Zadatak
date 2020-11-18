import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthComponent } from './auth/auth/auth.component';

import { PostsComponent } from './post/posts/posts.component';

const routes: Routes = [
  {path:'', redirectTo: '/auth', pathMatch: 'full'},
  { path: 'posts', component: PostsComponent },
  { path: '**', component: AuthComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
