import { Optional } from '@angular/core';
import { Inject } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/auth/auth/service/auth.service';
import { Post } from 'src/model/post';
import { PostsComponent } from '../../posts.component';
import { PostService } from '../../service/post.service';
import { Observable } from 'rxjs';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-new-post',
  templateUrl: './new-post.component.html',
  styleUrls: ['./new-post.component.css']
})
export class NewPostComponent implements OnInit {
  
  createForm: FormGroup;
  errorMessage = false
  post: Post = new Post();
  new: Post = new Post();
  messageError: any;
  par: any;
  user: any;
  postId: any;
  posts: Observable<Post[]>
  myDate: string;
  currentDate: Date;
  fiveMinutes: number = 5;
  pipe = new DatePipe('en-US');

  constructor(private formBuilder: FormBuilder,
    private postService: PostService,
    public dialogRef: MatDialogRef<PostsComponent>,
    private authService: AuthService,
    private toastr: ToastrService,
    @Optional() @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  local_data = this.data;

  ngOnInit() {
    this.createForm = this.formBuilder.group({
      text: new FormControl(null, Validators.required)
    });

    this.par = this.authService.getToken();


    this.authService.whoami(this.par).subscribe(
      data =>
        this.user = data
    )


  }

  save() {
    this.postService.add(this.post)
      .subscribe(data => {
        this.toastr.success('You have successfully added post!', 'Success');
        this.postService.allPosts().subscribe(
          data => {
            this.posts = data;
            console.log(this.posts)
          }
        )
      },
      error => {
      });
  }

  onSubmit() {
    this.post.text = this.createForm.value.text;
    this.post.user = this.user.username;
    console.log(this.post.user)
    this.save();
  }

  onSubmitUpdate(post: Post) {
    this.currentDate = new Date();
    let datum = post.postDate.toString();
    let minute = Number(datum.substring(14, 16));
    console.log(this.currentDate.getMinutes() + ' ' +  minute + ' ' + datum)
    if ((this.currentDate.getMinutes() - minute) >= 5) {
      if (this.user.username == post.user) {
        this.postService.update(post.id, this.local_data).subscribe(
          data => {
            this.toastr.success('You have successfully edited post!', 'Success')
          },
          error => {
          }
        );
      } else {
        this.toastr.error('You cannot edit a ' + 'post that is not from you', 'Error');
      }
    } else {
      this.toastr.error('You can edit a ' + 'post after 5 minutes', 'Error');
    }

  }


  back() {
    this.errorMessage = false;

  }

  postUpdateForm = new FormGroup({
    text: new FormControl({ value: this.local_data.text, disabled: false }, Validators.required)
  });

  onNoClick() {
    this.dialogRef.close();
  }


}
