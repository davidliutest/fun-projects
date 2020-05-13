// Canvas
var canvas = document.getElementById("canvas");
document.getElementById("canvas").addEventListener("click", addShape);
var ctx = canvas.getContext("2d");

// Resize window
function resize() {
  canvas.width = Math.max(400, window.innerWidth);
  canvas.height = Math.max(400, window.innerHeight);
  width = canvas.width;
  height = canvas.height;
}

// Update
function update() {
  // Window components
  // resize();

  // Game components
  updateShapes()
}

// Render
function draw() {
  // Refresh screen
  ctx.clearRect(0, 0, width, height);
  ctx.fillStyle = "white";
  ctx.fillRect(0, 0, width, height);

  drawShapes()
}

// Main game loop variables
//   Last time loop was run and FPS
var previousTime = 0,
    fpsCap = 60,
    lagTime = 0,
    timestep = 1000 / fpsCap,
    updateStepsCount = 0,
    updateStepsCountMax = 300;

// Main game loop
function loop(timestamp) {
  // Track time not simulated in gam
  lagTime += timestamp - previousTime;
  previousTime = timestamp;

  // Simulate time in fixed chunks according to timestep
  //   by updating game
  while(lagTime >= timestep) {
    update(timestep);
    lagTime -= timestep;
    // Checks if update steps is called too much
    updateStepsCount++;
    if(updateStepsCount >= updateStepsCountMax) {
      lagTime = 0;
      break;
    }
  }

  // Render
  draw();

  // Loop again
  requestAnimationFrame(loop);
}

// Init call
function init() {
  resize();
  requestAnimationFrame(loop);
}

init();

//////////////////////////////////////////////////////////////

// Circle Test
var shapeList =
[ {x: 55, y: 55, radius: 33, mass: 1, vx: 8, vy: 15, bx: 0, by: 0},
  {x: 221, y: 221, radius: 33, mass: 1, vx: 2, vy: -15, bx: 0, by: 0},
  {x: 321, y: 221, radius: 33, mass: 1, vx: 2, vy: -15, bx: 0, by: 0},
  {x: 421, y: 221, radius: 33, mass: 1, vx: 2, vy: -15, bx: 0, by: 0}];

// Update circle
function updateShapes() {
  for(var i = 0; i < shapeList.length; i++) {
    var shape = shapeList[i];
    displace(shape);
    collisionScreen(shape);
    for(var j = i+1; j < shapeList.length; j++)
      collisionShape(shape, shapeList[j]);
  }
}

function drawShapes() {
  for(var i = 0; i < shapeList.length; i++) {
    var shape = shapeList[i];
    ctx.beginPath();
    ctx.fillStyle = "red";
    ctx.arc(shape.x, shape.y, shape.radius, 0, Math.PI * 2);
    ctx.fill();
  }
}

function shapeEqual(shape1, shape2) {
  return shape1.x == shape2.x && shape1.y == shape2.y;
}

var gravity = 0.8;

function displace(shape) {
  shape.x += shape.vx;
  shape.y += shape.vy;
  shape.vy += gravity;
}

// Euclidean distance
function distance(x1, y1, x2, y2) {
  return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
}

// Magnitude of vector
function magnitude(vec) {
  return Math.sqrt(Math.pow(vec.x, 2) + Math.pow(vec.y, 2));
}

function vecAdd(vec1, vec2) {
  return {x: vec1.x + vec2.x, y: vec1.y + vec2.y};
}

function vecSub(vec1, vec2) {
  return {x: vec1.x - vec2.x, y: vec1.y - vec2.y};
}

function vecNeg(vec) {
  return {x: -vec.x, y: -vec.y};
}

function vecMult(vec, mag) {
  return {x: mag * vec.x, y: mag * vec.y};
}

function unitVec(vec) {
  var mag = magnitude(vec);
  if(mag != 0) {
    return {x: vec.x / mag, y: vec.y / mag};
  } else {
    return {x: 1, y: 1};
  }
}

// Dot product of 2D vectors
function dot(vec1, vec2) {
  return vec1.x * vec2.x + vec1.y * vec2.y;
}

// Project vec1 on vec2
function project(vec1, vec2) {
  return vecMult(vec2, dot(vec1, vec2) / Math.pow(magnitude(vec2), 2));
}

function getPosVec(shape) {
  return {x: shape.x, y: shape.y};
}

// Get vector from shape
function getVec(shape) {
  return {x: shape.vx, y: shape.vy};
}

function setVec(shape, vec) {
  shape.vx = vec.x;
  shape.vy = vec.y;
}

function collisionShape(shape1, shape2) {
  var distCenter = distance(shape1.x, shape1.y, shape2.x, shape2.y);
  var distRadius = shape1.radius + shape2.radius;
  if(distCenter <= distRadius) {
    var distOverlap = distRadius - distCenter;
    if(distOverlap != 0) {
      collisionShift(shape1, shape2, distOverlap);
    }
    var vecCenters = {x: shape2.x - shape1.x, y: shape2.y - shape1.y};
    var vecx1 = project(getVec(shape1), vecCenters);
    var vecy1 = vecSub(getVec(shape1), vecx1);
    var vecx2 = project(getVec(shape2), vecCenters);
    var vecy2 = vecSub(getVec(shape2), vecx2);
    var vecn1 = vecAdd(vecx2, vecy1);
    var vecn2 = vecAdd(vecx1, vecy2);
    setVec(shape1, vecn1);
    setVec(shape2, vecn2);
  }
}

function collisionShift(shape1, shape2, shift) {
  var dir = unitVec(vecSub(getPosVec(shape2), getPosVec(shape1)));
  var shiftVec = vecMult(dir, shift);
  shape2.x += shiftVec.x;
  shape2.y += shiftVec.y;
  /*
  for(var i = 0; i < shapeList.length; i++) {
    var shape3 = shapeList[i];
    if(!shapeEqual(shape2, shape3)) {
      var distCenter = distance(shape2.x, shape2.y, shape3.x, shape3.y);
      var distRadius = shape2.radius + shape3.radius;
      if(distCenter < distRadius) {
        collisionShift(shape2, shape3, distRadius - distCenter);
      }
    }
  }
  */
}

var friction = 0.8;

function collisionScreen(shape) {
  if(shape.x - shape.radius <= 0)
    shape.vx = Math.abs(shape.vx) * friction;
  if(shape.x + shape.radius >= width)
    shape.vx = -Math.abs(shape.vx) * friction;
  if(shape.y - shape.radius <= 0) {
    shape.vy = Math.abs(shape.vy) * friction;
  }
  if(shape.y + shape.radius > height) {
    // Sketchy stopping
    if(shape.by < 1000)
      shape.by++;
    shape.vy = -Math.abs(shape.vy) *
                Math.max(0, (friction - shape.by * 0.05));
    if(Math.abs(shape.vy) < 0.001) {
      shape.vx *= 0.99;
    }
    // console.log(shape.vx + " " + shape.vy);
    shape.y = height - shape.radius;
  }
}

var curBalls = 0;
var maxBalls = 15;

function addShape(e) {
  if(curBalls < maxBalls) {
    var cx = event.clientX;
    var cy = event.clientY;
    var r = 33;
    var valid = true;
    for(var i = 0; i < shapeList.length; i++) {
      var s = shapeList[i];
      var dist = distance(cx, cy, s.x, s.y);
      var minDist = r + s.radius;
      if(dist < minDist) {
        valid = false; break;
      }
    }
    if(valid) {
      var vx = Math.floor(Math.random() * 21) - 10;
      var vy = Math.floor(Math.random() * 21) - 10;
      var shape =
        {x: cx, y: cy, radius: r, mass: 1, vx: vx, vy: vy, bx: 0, by: 0};
      shapeList.push(shape);
      curBalls++;
    }
  }
}
