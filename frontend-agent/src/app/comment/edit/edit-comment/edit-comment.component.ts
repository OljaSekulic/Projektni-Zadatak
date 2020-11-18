import { Optional } from '@angular/core';
import { Inject } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AuthService } from 'src/app/auth/auth/service/auth.service';
import { PostsComponent } from 'src/app/post/posts/posts.component';
import { CommentService } from '../../new-comment/service/comment.service';
import { Comment } from '../../../../model/comment';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-edit-comment',
  templateUrl: './edit-comment.component.html',
  styleUrls: ['./edit-comment.component.css']
})
export class EditCommentComponent implements OnInit {


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
    @Optional() @Inject(MAT_DIALOG_DATA) public data: Comment) { }


  local_data = this.data;

  ngOnInit(): void {

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

  onSubmitUpdate(comment: Comment) {
    console.log(this.local_data.userName + ' ' + this.user.username)
    console.log(comment);
    if (this.local_data.userName == this.user.username) {

      this.commentService.update(comment.id, this.local_data).subscribe(
        data => {
          this.new.content = this.commentUpdateForm.value.content;
          this.toastr.success('You have successfully edited comment!', 'Success')
        },
        error => {
          this.toastr.error('Error!', 'Error');
        });
    }
    else {
      this.toastr.error('You cannot edit a ' + 'comment that is not from you', 'Error');
    }
  }

  commentUpdateForm = new FormGroup({
    content: new FormControl({ value: this.local_data.content, disabled: false }, Validators.required)
  });

  onNoClick() {
    this.dialogRef.close();
  }

}
