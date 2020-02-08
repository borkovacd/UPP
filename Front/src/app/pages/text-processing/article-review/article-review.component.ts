import { Component, OnInit } from '@angular/core';
import {TextProcessingService} from '../../../service/text-processing.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-article-review',
  templateUrl: './article-review.component.html',
  styleUrls: ['./article-review.component.css']
})
export class ArticleReviewComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private processInstanceId = null;
  private enumValues = [];
  private tasks = [];
  private claimedTaskId = null;

  constructor(private textProcessingService: TextProcessingService,
              public router: Router,
              private route: ActivatedRoute) {

  }

  ngOnInit() {

    const taskId = this.route.snapshot.params.taskId;
    const x = this.textProcessingService.loadTask(taskId);

    x.subscribe(
      res => {
        console.log(res);
        console.log('Ispis rezultata');
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        console.log(this.formFields);
        this.processInstanceId = res.processInstanceId;
      },
      err => {
        console.log('Error occured');
      }
    );
  }

  onSubmit(value, form) {
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId: property, fieldValue: value[property]});
    }

    console.log(o);
    let x = this.textProcessingService.articleReviewDecision(o, this.formFieldsDto.taskId);


    x.subscribe(
      res => {
        console.log("Article rejected: "  + res);
        if (res == false) {
          alert("Odlucili ste da je rad tematski prihvatljiv.");
          this.router.navigateByUrl('/pdf-review/' + this.processInstanceId);
        } else {
          alert("Odlucili ste da se rad odbija, jer nije tematski prihvatljiv");
          this.router.navigateByUrl('/article-final');
        }
      },
      err => {
        console.log("Error occured");
      }
    );
  }
}
