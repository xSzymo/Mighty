package acceptance

class Requester {
    def static request(address, json) {
        def post = new URL(address).openConnection()
        def message = json
        post.setDoOutput(true)
        post.setRequestProperty("Content-Type", "application/json")
        post.outputStream.write message.getBytes("UTF-8")
        post.getResponseCode()

        return post
    }

    def static request(address, token, method) {
        def post = new URL(address).openConnection()
        post.setDoOutput(true)
        post.setRequestMethod(method)
        post.setRequestProperty("authorization", "Bearer " + token)
        post.getResponseCode()

        return post
    }
}
