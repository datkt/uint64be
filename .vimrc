let NERDTreeIgnore = [ 'META-INF', '\.class$', 'tape/' ]
let b:ale_linters = { 'kotlin': [ 'ktlint' ] }

let g:syntastic_kotlin_kotlinc_options = [ ]
let g:syntastic_kotlin_kotlinc_sourcepath = './'

let g:ale_kotlin_kotlinc_sourcepath = '.'

"let g:ale_c_build_dir = 'build/'
