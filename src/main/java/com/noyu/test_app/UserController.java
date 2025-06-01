package com.noyu.test_app;

import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;





@Controller
public class UserController {
    
    @Autowired // UserRepositoryを自動的に注入
    private UserRepository userRepository;

    /**
     * ユーザー登録フォームを表示するGETクエストを処理
     * @param model モデルオブジェクト
     * @return ユーザー登録フォームのテンプレート名
     */
    @GetMapping("/")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userForm", new UserForm()); // 空のUserFormオブジェクトを生成
        return "user-registration"; // user-registration.html テンプレートを返す
    }
    
    /**
     * ユーザー登録フォームからのPOSTリクエストを処理
     * @param userForm フォームから送信されたユーザーデータ
     * @param bindingResult バリデーション結果
     * @param model モデルオブジェクト
     * @return リダイレクト先またはテンプレート名
     */
    @PostMapping("/register") // register URLへのPOSTリクエストを処理
    public String registerUser(@Valid @ModelAttribute("userForm") UserForm userForm,
                                BindingResult bindingResult,
                                Model model) {

        // 入力形式のチェック
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "入力内容にエラーがあります。");
            model.addAttribute("messageType", "error");
            return "user-registration";
        }

        // メールアドレスの重複チェック
        Optional<User> existingUser = userRepository.findByEmail(userForm.getEmail());
        if (existingUser.isPresent()) { // ユーザーがすでに存在する場合
            bindingResult.rejectValue("email", "email.duplicate", "このメールアドレスは既に登録されています。");
            model.addAttribute("message", "ユーザー登録に失敗しました。");
            model.addAttribute("messageType", "error");
            return "user-registration";
        }

        // UserFormからUserエンティティを作成
        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());

        try {
            userRepository.save(user); // データベースに保存
            model.addAttribute("message", "ユーザー登録が完了しました！");
            model.addAttribute("messageType", "success");
            model.addAttribute("userForm", new UserForm()); // フォームをクリア
            System.out.println("登録されたユーザー: " + user); // ログ
        } catch (Exception e) {
            model.addAttribute("message", "ユーザー登録に失敗しました。メールアドレスがすでに登録されている可能性があります。");
            model.addAttribute("messageType", "error");
            model.addAttribute("userForm", userForm);
            System.err.println("登録エラー: " + e.getMessage());
            e.printStackTrace();
        }

        return "user-registration"; // フォームページに戻る
    }
}