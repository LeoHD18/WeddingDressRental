package com.fashion.weddingdressrental;

import java.io.*;
import java.util.UUID;

public class SocialMediaTeam {

    private static final String SOCIAL_MEDIA_FILE = "social_media_posts.txt";

    // Method to post a promotion to social media
    public void postToSocialMedia(Promotion promotion) {
        String postId = generatePostId();
        String postContent = createPostContent(postId, promotion);

        // Display the post on the console
        System.out.println(postContent);

        // Save the post to the social media file
        savePostToFile(postContent);
    }

    // Generate a unique ID for each social media post
    private String generatePostId() {
        return "POST-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // Create aesthetic post content (no emojis/icons)
    private String createPostContent(String postId, Promotion promotion) {
        StringBuilder post = new StringBuilder();
        post.append("\n=========================================\n");
        post.append("         SOCIAL MEDIA PROMOTION          \n");
        post.append("=========================================\n");
        post.append("Post ID        : ").append(postId).append("\n");
        post.append("Promotion      : ").append(promotion.getPromoDetails()).append("\n");
        post.append("Discount       : ").append(promotion.getDiscountPercentage()).append("% OFF\n");
        post.append("Posted On      : ").append(java.time.LocalDate.now()).append("\n");
        post.append("=========================================\n");
        post.append("Take advantage of this exclusive offer now!\n");
        post.append("=========================================\n");
        return post.toString();
    }

    // Save the post to a file
    private void savePostToFile(String postContent) {
        File file = new File(SOCIAL_MEDIA_FILE);

        // Ensure the file exists
        try {
            if (!file.exists() && file.createNewFile()) {
                System.out.println("Social media file created: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            System.out.println("Error creating social media file: " + e.getMessage());
            return;
        }

        // Append the post to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(postContent);
        } catch (IOException e) {
            System.out.println("Error saving social media post: " + e.getMessage());
        }
    }
}
