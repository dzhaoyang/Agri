(function() {
var e = {}, t = {}, n = {}, r = function(e, n, r) {
if (t[e]) return;
t[e] = r;
var i = 3;
while (i--) try {
moon.setItem(moon.prefix + e, r.toString()), moon.setItem(moon.prefix + e + "_ver", moon_map[e]);
break;
} catch (s) {
moon.clear();
}
}, i = function(e) {
if (!e || !t[e]) return null;
var r = t[e];
return typeof r == "function" && !n[e] && (r = t[e] = r(i), n[e] = !0), r;
}, s = function(e, t) {
var n = i(e);
t && t(n);
};
e.use = function(t, n) {
e.run = function() {
s(t, n);
};
}, window.define = r, window.use = s, window.seajs = e;
})(), function(e) {
function t(e, t, r) {
if (typeof e != "object") return;
var i = Object.prototype.toString.call(e).replace(/^\[object (.+)\]$/, "$1");
r = r || e;
if (i == "Array") {
for (var s = 0, o = e.length; s < o; ++s) if (t.call(r, e[s], s, e) === !1) return;
} else {
if (i !== "Object" && n != e) throw "unsupport type";
if (n == e) {
for (var s = 0; s < e.length; ++s) {
var u = n.key(s), a = n.getItem(u);
if (t.call(r, a, u, e) === !1) return;
}
return;
}
for (var s in e) {
if (!e.hasOwnProperty(s)) continue;
if (t.call(r, e[s], s, e) === !1) return;
}
}
}
var n = e.localStorage, r = document.head || document.getElementsByTagName("head")[0], i = {
prefix: "__MOON__",
loaded: [],
unload: [],
hit_num: 0,
mod_num: 0,
init: function() {
i.loaded = [], i.unload = [];
var r, s, o;
location.search.indexOf("no_moon=1") != -1 && i.clear(), t(moon_map, function(t, u) {
s = i.prefix + u, o = t, r = !!n && n.getItem(s), i.mod_num++;
if (!r || o != n.getItem(s + "_ver")) i.unload.push(o.replace(/^http(s)?:\/\/res.wx.qq.com/, "")); else try {
e.eval.call(e, 'define("' + u + '",[],' + r + ")"), i.hit_num++;
} catch (a) {
i.unload.push(o.replace(/^http(s)?:\/\/res.wx.qq.com/, ""));
}
}), i.load(i.genUrl());
},
genUrl: function() {
var e = i.unload;
if (!e || e.length <= 0) return [];
var t = "http://res.wx.qq.com" + e[0], n = [], r;
for (var s = 1, o = e.length; s < o; ++s) r = t + "," + e[s], r.length > 1024 ? (n.push(t), t = "http://res.wx.qq.com" + e[s]) : t = r;
return n.push(t), n;
},
load: function(e) {
if (!e || e.length <= 0) {
seajs.use = window.use;
return;
}
var n = 0;
t(e, function(t) {
var i = document.createElement("script");
i.src = t, i.type = "text/javascript", i.async = !0, i.onload = i.onreadystatechange = function() {
i && (!i.readyState || /loaded|complete/.test(i.readyState)) && (n++, i.onload = i.onreadystatechange = null, n == e.length && seajs.run());
}, r.appendChild(i);
});
},
setItem: function(e, t) {
!!n && n.setItem(e, t);
},
clear: function() {
if (!n) return;
t(n, function(e, t) {
~t.indexOf(i.prefix) && n.removeItem(t);
});
}
};
window.moon = i;
}(window), window.moon.init();