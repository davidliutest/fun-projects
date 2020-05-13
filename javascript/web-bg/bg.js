// Canvas
var canvas = document.getElementById("canvas");
window.addEventListener("resize", resize);
var ctx = canvas.getContext("2d");

var w, h;

resize();

function resize() {
  canvas.width = window.innerWidth;
  canvas.height = window.innerHeight;
  w = canvas.width;
  h = canvas.height;
  gen();
}

function gen() {
  ctx.fillStyle = "#303F9F";
  ctx.fillRect(0, 0, w, h);

  /*
  ctx.beginPath();
  ctx.arc(w * 0.15, h * 0.75,
          h * 0.075, 0, 2 * Math.PI);
  ctx.lineWidth = 25;
  ctx.strokeStyle = "#1A237E";
  ctx.closePath();
  ctx.stroke();

  ctx.beginPath();
  ctx.arc(w * 0.75, h * 0.25,
          h * 0.075, 0, 2 * Math.PI);
  ctx.lineWidth = 25;
  ctx.strokeStyle = "#1A237E";
  ctx.closePath();
  ctx.stroke();
  */

  ctx.rotate(20 * Math.PI / 180);
  ctx.fillStyle = "#1A237E";
  var hyp = Math.sqrt(Math.pow(w, 2) + Math.pow(h, 2));
  ctx.fillRect(0, 0, hyp, h / 3);
  ctx.rotate(-20 * Math.PI / 180);

  ctx.rotate(45 * Math.PI / 180);
  ctx.fillStyle = "#3F51B5";
  var hyp = Math.sqrt(Math.pow(w, 2) + Math.pow(h, 2));
  ctx.fillRect(0, 0, hyp, h / 6);
  ctx.rotate(-45 * Math.PI / 180);

  ctx.rotate(35 * Math.PI / 180);
  ctx.fillStyle = "#283593";
  var hyp = Math.sqrt(Math.pow(w, 2) + Math.pow(h, 2));
  ctx.fillRect(0, 0, hyp, 50);
  ctx.rotate(-35 * Math.PI / 180);
  document.getElementById("front").style.background = 'url(' + canvas.toDataURL() + ')';
}
