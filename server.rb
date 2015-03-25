require 'sinatra'
require 'sinatra/reloader'
require 'json'

bookmarks = [{ id:"1", name:"オキニ1", url:"http:/hogehoge.com/okini1"},
             { id:"2", name:"オキニ2", url:"http:/hogehoge.com/okini2"},
             { id:"3", name:"オキニ3", url:"http:/hogehoge.com/okini3"}]

get '/bookmarks' do
  content_type :json, charset: "utf-8"
  bookmarks.to_json
end

get '/bookmarks/:id' do
  content_type :json, charset: "utf-8"
  target = {}
  bookmarks.each do |bookmark|
    if bookmark[:id] == params[:id]
      target = bookmark
    end
  end
  target.to_json
end