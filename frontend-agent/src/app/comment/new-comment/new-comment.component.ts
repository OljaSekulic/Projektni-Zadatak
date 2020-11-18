import { Optional } from '@angular/core';
import { Inject } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AuthService } from 'src/app/auth/auth/service/auth.service';
import { PostsComponent } from 'src/app/post/posts/posts.component';
import { CommentService } from './service/comment.service';
import { Comment } from '../../../model/comment';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-new-comment',
  templateUrl: './new-comment.component.html',
  styleUrls: ['./new-comment.component.css']
})
export class NewCommentComponent implements OnInit {
  commentForm: FormGroup;
  success = false;
  errorMessage = false
  comment: Comment = new Comment();
  new: Comment = new Comment();
  messageError: any;
  par: any;
  user: any;
  postId: any;

  constructor(private formBuilder: FormBuilder,
    private commentService: CommentService,
    public dialogRef: MatDialogRef<PostsComponent>,
    private authService: AuthService,
    private toastr: ToastrService,
    @Optional() @Inject(MAT_DIALOG_DATA) public data: any) { }

  local_data = this.data;


  ngOnInit() {
    console.log(this.local_data)
    this.commentForm = this.formBuilder.group({
      content: new FormControl(null, Validators.required)
    });

    this.par = this.authService.getToken();


    this.authService.whoami(this.par).subscribe(
      data =>
        this.user = data
    )


  }

  save(com: Comment) {
    this.commentService.add(this.local_data.id, com)
      .subscribe(data => {
        this.toastr.success('You have successfully added comment!', 'Success')
      },

        error => {
        });
  }

  onSubmit() {
    this.comment.content = this.commentForm.value.content;
    this.comment.userName = this.user.username;
    console.log(this.comment)
    this.save(this.comment);
  }


  onNoClick() {
    this.dialogRef.close();
  }


  back() {
    this.errorMessage = false;
    this.success = false;

  }



}
